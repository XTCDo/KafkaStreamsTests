defmodule ElixirApp do
  @moduledoc false
  use Application

  def start(_type, _args) do
    import Supervisor.Spec

    gen_consumer_impl = Pipe
    topic_names=["streams-plaintext-input","streams-elixir-output"]

    children = [
      gen_consumer_impl
    ]
    Supervisor.start_link(children, strategy: :one_for_one)
  end
end