Embulk::JavaPlugin.register_decoder(
  "remove_nonstandard_utf8_bytes", "org.embulk.decoder.remove_nonstandard_utf8_bytes.RemoveNonstandardUtf8BytesDecoderPlugin",
  File.expand_path('../../../../classpath', __FILE__))
