package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;
import be.nabu.libs.types.base.Scope;

// allows you to determine the scope of something you defined. public means everyone can see it, private means a select audience can see it
// what that audience is, is up to you
// in the future we might add more scopes if relevant (e.g. protected)
public class ScopeProperty extends SimpleProperty<Scope> implements PropertyWithDefault<Scope> {

	private static ScopeProperty instance = new ScopeProperty();
	
	public static ScopeProperty getInstance() {
		return instance;
	}
	
	public ScopeProperty() {
		super(Scope.class);
	}

	@Override
	public String getName() {
		return "scope";
	}

	@Override
	public Scope getDefault() {
		return Scope.PUBLIC;
	}

}
