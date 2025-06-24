package domain.shared.abstractions;

public interface CommandHandler<C extends Command, Result> {
    Result handle(C command);
}
