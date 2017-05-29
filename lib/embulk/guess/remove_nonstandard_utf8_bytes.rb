module Embulk
  module Guess

    # TODO implement guess plugin to make this command work:
    #      $ embulk guess -g "remove_nonstandard_utf8_bytes" partial-config.yml

    # class RemoveNonstandardUtf8Bytes < GuessPlugin
    #   Plugin.register_guess("remove_nonstandard_utf8_bytes", self)
    #
    #   FOO_BAR_HEADER = "\x1f\x8b".force_encoding('ASCII-8BIT').freeze
    #
    #   def guess(config, sample_buffer)
    #     if sample_buffer[0,2] == FOO_BAR_HEADER
    #       return {"decoders" => [{"type" => "remove_nonstandard_utf8_bytes"}]}
    #     end
    #     return {}
    #   end
    # end

  end
end
