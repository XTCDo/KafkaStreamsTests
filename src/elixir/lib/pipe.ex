defmodule Pipe do
  @moduledoc false
  use KafkaEx.GenConsumer
  alias KafkaEx.Protocol.Fetch.Message

  require Logger

  #note: messages are delivered in batches -> check out OTP + KafkaEx or ask Chiel/Argu
  def handle_message_set(message_set, state) do
    for %Message{value: message} <- message_set do
      Logger.debug(fn-> "message: " <> inspect(message) end )
      end
    {:async_commit, state}
  end
end
