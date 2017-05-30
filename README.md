# Remove Nonstandard Utf8 Bytes decoder plugin for Embulk

TODO: Write short description here and build.gradle file.

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

(If guess supported) you don't have to write `decoder:` section in the configuration file. After writing `in:` section, you can let embulk guess `decoder:` section using this command:

```
$ embulk gem install embulk-decoder-remove_nonstandard_utf8_bytes
$ embulk guess -g remove_nonstandard_utf8_bytes config.yml -o guessed.yml
```

## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```
