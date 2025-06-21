package domain.orders.abstractions;

public class OrderBuilderFactory {
    public static OrderBuilder getOrder(String orderBuilderClass) {
        Class classe = null;
        Object objeto = null;

        try {
            classe = Class.forName("domain.orders.builders." + orderBuilderClass);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Construtor de Pedido inexistente");
        }

        if (!(objeto instanceof OrderBuilder)) {
            throw new IllegalArgumentException("Construtor de Pedido inválido");
        }

        OrderBuilder order = (OrderBuilder) objeto;

        return order;
    }
}
