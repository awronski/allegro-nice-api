package com.apwglobal.nice.domain;

import com.apwglobal.bd.BD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Payment {

    private long transactionId;
    private long buyerId;
    private long sellerId;

    private String email;
    private Date date;

    private double amount;
    private double postageAmount;
    private double paymentAmount;

    private Boolean withInvoice;
    private Optional<String> msg;
    private long payId;
    private String payStatus;
    private Shipment shipment;

    private Address orderer;
    private Address receiver;

    private List<Item> items;

    private boolean processed;

    public Payment() { }

    private Payment(Builder builder) {
        transactionId = builder.transactionId;
        buyerId = builder.buyerId;
        sellerId = builder.sellerId;
        email = builder.email;
        amount = builder.amount;
        date = builder.date;
        postageAmount = builder.postageAmount;
        paymentAmount = builder.paymentAmount;
        withInvoice = builder.withInvoice;
        msg = builder.msg;
        payId = builder.payId;
        payStatus = builder.payStatus;
        shipment = builder.shipment;
        orderer = builder.orderer;
        receiver = builder.receiver;
        items = builder.items;
        processed = builder.processed;
    }

    public long getTransactionId() {
        return transactionId;
    }
    public long getBuyerId() {
        return buyerId;
    }
    public String getEmail() {
        return email;
    }
    public Date getDate() {
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public double getPostageAmount() {
        return postageAmount;
    }
    public double getPaymentAmount() {
        return paymentAmount;
    }
    public Boolean getWithInvoice() {
        return withInvoice;
    }
    public Optional<String> getMsg() {
        return msg;
    }
    public long getPayId() {
        return payId;
    }
    public String getPayStatus() {
        return payStatus;
    }
    public Shipment getShipment() {
        return shipment;
    }
    public Address getReceiver() {
        return receiver;
    }
    public Address getOrderer() {
        return orderer;
    }
    public List<Item> getItems() {
        return items;
    }
    public boolean isProcessed() {
        return processed;
    }

    public long getSellerId() {
        return sellerId;
    }

    public static final class Builder {
        private long buyerId;
        private long sellerId;
        private String email;
        private Date date;
        private double amount;
        private double postageAmount;
        private double paymentAmount;
        private Boolean withInvoice;
        private Optional<String> msg;
        private long payId;
        private String payStatus;
        private Shipment shipment;
        private Address orderer;
        private Address receiver;
        private long transactionId;
        private List<Item> items;
        private boolean processed;

        public Builder() {
        }

        public Builder buyerId(long buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder date(String date) {
            try {
                this.date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
            } catch (ParseException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
            return this;
        }

        public Builder amount(float amount) {
            this.amount = new BD(amount).doubleValue();
            return this;
        }

        public Builder postageAmount(float postageAmount) {
            this.postageAmount = new BD(postageAmount).doubleValue();
            return this;
        }

        public Builder paymentAmount(float paymentAmount) {
            this.paymentAmount = new BD(paymentAmount).doubleValue();;
            return this;
        }

        public Builder msg(Optional<String> msg) {
            this.msg = msg;
            return this;
        }

        public Builder withInvoice(int withInvoice) {
            this.withInvoice = withInvoice == 1;
            return this;
        }

        public Builder msg(String msg) {
            if (msg.isEmpty()) {
                this.msg = Optional.empty();
            } else {
                this.msg = Optional.of(msg);
            }
            return this;
        }

        public Builder payId(long payId) {
            this.payId = payId;
            return this;
        }

        public Builder payStatus(String payStatus) {
            this.payStatus = payStatus;
            return this;
        }

        public Builder shipmentId(int shipmentId) {
            this.shipment = Shipment.VALUES.get(shipmentId);
            return this;
        }

        public Builder orderer(Address orderer) {
            this.orderer = orderer;
            return this;
        }

        public Builder receiver(Address receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder transactionId(long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder items(List<Item> items) {
            this.items = items;
            return this;
        }

        public Builder processed(boolean processed) {
            this.processed = processed;
            return this;
        }

        public Builder sellerId(long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Payment build() {
            if (!this.withInvoice) {
                this.orderer = receiver;
            }
            return new Payment(this);
        }

    }

    @Override
    public String toString() {
        return "Payment{" +
                "transactionId=" + transactionId +
                ", email='" + email + '\'' +
                ", amount=" + amount +
                ", paymentAmount=" + paymentAmount +
                ", withInvoice=" + withInvoice +
                ", payStatus='" + payStatus + '\'' +
                ", items.size='" + (items != null ? items.size() : 0) + '\'' +
                ", processed=" + processed +
                '}';
    }

}
