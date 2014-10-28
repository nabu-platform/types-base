# Types Base

This is a base package that implements a lot of functionality concerning types.

It defines a lot "simple" types and provides the bridge between what we know as XML Schema types and java bean representations. It also defines all the attributes that define such a type (for example a date would have timezone, language, format,...). Using these types definitions it adds some automatic conversions that are not possible at a lower (converter-api) level because of missing metadata (e.g. string <> date)

It also defines all of the validation properties we usually link to XML Schema like minOccurs, maxOccurs, maxLength etc.

Additionally it provides a lot of collection handlers that are used to automatically manage stuff like lists, collections,...

Lastly it provides a lot of base implementations for higher level packages.
