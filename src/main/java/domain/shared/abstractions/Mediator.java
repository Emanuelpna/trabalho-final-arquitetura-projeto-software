package domain.shared.abstractions;

public interface Mediator<Result> {
    <C extends Command, CH extends CommandHandler<C, Result>> Result send(C command, CH commandHandler);
}
