# Disabler

A library to allow client-side mods to easily be disabled from the server.

## Server-side Configuration
All rules are stored in a file at `config/disabler-rules.json`.

The format for disabling is as follows:
```json
{
  "modid": {
    "feature": true
  }
}
```
The feature will be disabled if the value is `true`.

## API Use
Simply call `DisableListenerRegistry#register` with all arguments, for each feature.
For an example, see the [testmod](https://github.com/AMereBagatelle/Disabler/blob/master/src/testmod/java/io/github/amerebagatelle/disablertest/TestModClient.java).