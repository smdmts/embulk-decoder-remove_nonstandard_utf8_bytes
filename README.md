# Remove nonstandard UTF-8 Bytes decoder plugin for Embulk.

Remove broken encoding of UTF-8 plugin for the Embulk.

- Ignoring malformed input.
- Ignoring unmappable character.


## Overview

* **Plugin type**: decoder
* **Guess supported**: no

## Example

```yaml
in:
  type: any output input plugin type
  decoders:
    - type: remove_nonstandard_utf8_bytes
```

## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```
