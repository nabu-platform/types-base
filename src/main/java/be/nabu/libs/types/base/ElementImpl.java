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
import be.nabu.libs.validator.api.Validation;
import be.nabu.libs.validator.api.Validator;

abstract public class ElementImpl<T> extends BaseTypeInstance implements Element<T> {

	private Set<Property<?>> blockedProperties = new HashSet<Property<?>>();
	
	private ComplexType parent;
	
	/**
	 * Cached values for quick access
	 */
	private String name, namespace;
	
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
	public List<? extends Validation<?>> validate(T instance) {
		CollectionHandlerProvider<T, ?> provider = (CollectionHandlerProvider<T, ?>) CollectionHandlerFactory.getInstance().getHandler().getHandler(instance.getClass());
		// if there is a provider for it, it is a collection
		if (provider != null) {
			Validator<Collection<?>> collectionValidator = getType().createCollectionValidator(getProperties());
			Collection<?> collection = provider.getAsCollection(instance);
			List<Validation<?>> messages = new ArrayList<Validation<?>>(collectionValidator.validate(collection));
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
		properties.add(QualifiedProperty.getInstance());
		properties.add(NameProperty.getInstance());
		properties.add(NamespaceProperty.getInstance());
		properties.removeAll(blockedProperties);
		return properties;
	}
	
	public Set<Property<?>> getBlockedProperties() {
		return blockedProperties;
	}
	
	@Override
	public String getName() {
		if (name == null) {
			name = ValueUtils.getValue(new NameProperty(), getProperties());
			if (name == null) {
				name = getType().getName(getProperties());
			}
		}
		return name;
	}
	@Override
	public String getNamespace() {
		if (namespace == null) {
			namespace = ValueUtils.getValue(new NamespaceProperty(), getProperties());
			if (namespace == null && getParent() != null) {
				namespace = getParent().getNamespace();
			}
		}
		return namespace;
	}

	@Override
	public void setProperty(Value<?>... values) {
		// reset cache
		name = null;
		namespace = null;
		super.setProperty(values);
	}
	
}
