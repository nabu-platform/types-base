package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

// in some cases we have data where dynamic names are used as keys
// to be able to parse this into a static definition, we allow for dynamically named fields
/*
 * E.g.
 * 
 * {
	"<device_id_1>": {
		"metadata": {
			"deviceType":"<device_type>", 
			"version":"<FW_version>"
		}, "measurements": [{
			"timestamp":<UTC_epoch>, 
			"filling": { 
				"level": <filling_mm>
			}, 
			"temperatures": {
				"board": {
					"value": <temperature_board_x/10°C>
				}, 
				"ext1": { 
					"value": <temperature_external_1_x/10°C>
				},
				...
				"extN": {
				
				}
			}
		}]
	}
}


Both the root and the ext fields are dynamic in this example
We want to have a fixed defined array "devices" which contains for example a field "name"
We then configure the dynamic name property on that devices root array with value "name"
This means we will map dynamic data to that array and put the actual runtime name in the indicated field
 */
public class DynamicNameProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static DynamicNameProperty instance = new DynamicNameProperty();
	
	public static DynamicNameProperty getInstance() {
		return instance;
	}
	
	public DynamicNameProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "dynamicName";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
