package be.nabu.libs.types.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.BaseTypeInstance;
import be.nabu.libs.types.CollectionHandlerFactory;
import be.nabu.libs.types.api.CollectionHandlerProvider;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.properties.NameProperty;
import be.nabu.libs.types.properties.NamespaceProperty;
import be.nabu.libs.types.properties.QualifiedProperty;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.Validator;

abstract public class ElementImpl<T> extends BaseTypeInstance implements Element<T> {

	private Set<Property<?>> blockedProperties = new HashSet<Property<?>>();
	
	private ComplexType parent;
	
	public ElementImpl(Type type, ComplexType parent, Value<?>...values) {
		super(type);
		this.parent = parent;
		setProperty(values);
	}
	
	public ElementImpl(String name, Type type, ComplexType parent, Value<?>...values) {
		super(type);
		this.parent = parent;
		setProperty(values);
		setProperty(new ValueImpl<String>(new NameProperty(), name));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ValidationMessage> validate(T instance) {
		CollectionHandlerProvider<T, ?> provider = (CollectionHandlerProvider<T, ?>) CollectionHandlerFactory.getInstance().getHandler().getHandler(instance.getClass());
		// if there is a provider for it, it is a collection
		if (provider != null) {
			Validator<Collection<?>> collectionValidator = getType().createCollectionValidator(getProperties());
			Collection<?> collection = provider.getAsCollection(instance);
			List<ValidationMessage> messages = new ArrayList<ValidationMessage>(collectionValidator.validate(collection));
			Validator validator = getType().createValidator(getProperties());
			for (Object item : collection)
				messages.addAll(validator.validate(item));
			return messages;
		}
		else {
			Validator<T> validator = (Validator<T>) getType().createValidator(getProperties());
			return validator.validate(instance);
		}
	}

	@Override
	public ComplexType getParent() {
		return parent;
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties() {
		Set<Property<?>> properties = getType().getSupportedProperties(getProperties());
		properties.add(new QualifiedProperty());
		properties.add(new NameProperty());
		properties.add(new NamespaceProperty());
		properties.removeAll(blockedProperties);
		return properties;
	}
	
	protected Set<Property<?>> getBlockedProperties() {
		return blockedProperties;
	}
	
	@Override
	public String getName() {
		String name = ValueUtils.getValue(new NameProperty(), getProperties());
		return name == null ? getType().getName(getProperties()) : name;
	}
	@Override
	public String getNamespace() {
		String namespace = ValueUtils.getValue(new NamespaceProperty(), getProperties());
		if (namespace == null && getParent() != null)
			namespace = getParent().getNamespace();
		return namespace;
	}
}
