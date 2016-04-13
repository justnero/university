package ru.justnero.study.sevsu.pptc;

public class Bill extends Invoice {

    private boolean isPayed = false;

    Bill(Invoice invoice) {
        super(invoice);
    }

    Bill pay() {
        isPayed = true;
        return this;
    }

    Bill refund() {
        isPayed = false;
        return this;
    }

    public Bill takeItems(IConsumer consumer) {
        consumer.items(items, true);
        return this;
    }

    public Bill returnItems(IConsumer consumer) {
        consumer.items(items, false);
        return this;
    }

}
