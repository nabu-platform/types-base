package be.nabu.libs.types.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.PropertyWithDefault;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.Group;
import be.nabu.libs.types.properties.MaxOccursProperty;
import be.nabu.libs.types.properties.MinOccursProperty;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.ValidationMessage.Severity;
import be.nabu.libs.validator.api.Validator;

/**
 * A suboptimal choice: it will detect if the maxOccurs of your choice is smaller than the actual elements allowed
 * But once the child elements are lists or the maxOccurs is larger than the amount, it becomes useless
 * The problem is that to properly track the choice you need the insertion order of all the elements, this however, is non-trivial
 * It can not be tracked directly by the context content due to inheritance (each level must manage his own properties)
 * Ideally you would send out an event every time something is added/removed and an external entity would track this
 * However this introduces some overhead and in general an unlimited choice with elements that are themselves lists are rare
 */
public class Choice implements Group {

	private List<Element<?>> children = new ArrayList<Element<?>>();
	
	private Map<Property<?>, Value<?>> properties = new HashMap<Property<?>, Value<?>>();
	
	public Choice(Element<?>...children) {
		this.children.addAll(Arrays.asList(children));
	}
	
	@Override
	public Iterator<Element<?>> iterator() {
		return children.iterator();
	}

	@Override
	public Value<?>[] getProperties() {
		return properties.values().toArray(new Value<?>[properties.size()]);
	}

	public void setProperty(Value<?>...values) {
		for (Value<?> value : values) {
			if (value.getProperty() instanceof PropertyWithDefault && value.getValue() != null && value.getValue().equals(((PropertyWithDefault<?>) value.getProperty()).getDefault()))
				properties.remove(value.getProperty());
			else
				properties.put(value.getProperty(), value);
		}
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>... values) {
		Set<Property<?>> properties = new HashSet<Property<?>>();
		properties.add(new MinOccursProperty());
		properties.add(new MaxOccursProperty());
		return properties;
	}
	@Override
	public Validator<ComplexContent> createValidator(Value<?>... values) {
		return new ChoiceValidator();
	}

	private class ChoiceValidator implements Validator<ComplexContent> {
		@Override
		public List<ValidationMessage> validate(ComplexContent instance) {
			List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
			int maxOccurs = ValueUtils.getValue(new MaxOccursProperty(), getProperties());
			int minOccurs = ValueUtils.getValue(new MinOccursProperty(), getProperties());
			List<Object> values = new ArrayList<Object>();
			for (Element<?> child : Choice.this) {
				Object value = instance.get(child.getName());
				if (value != null)
					values.add(value);
			}
			if (values.size() > maxOccurs)
				messages.add(new ValidationMessage(Severity.ERROR, "The choice element only allows " + maxOccurs + " values, found " + values.size()));
			else if (values.size() < minOccurs)
				messages.add(new ValidationMessage(Severity.ERROR, "The choice element must contain at least " + minOccurs + " values, found " + values.size()));
			return messages;
				
		}
	
		@Override
		public Class<ComplexContent> getValueClass() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
