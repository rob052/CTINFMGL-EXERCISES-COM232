public class Account {
    private String accountStatus;
    private String dateSubscribed;
    private String subscriptionStatus;
    private String subscriptionType;

    public Account(String accountStatus, String dateSubscribed, String subscriptionStatus, String subscriptionType) {
        this.accountStatus = accountStatus;
        this.dateSubscribed = dateSubscribed;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionType = subscriptionType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(String dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}