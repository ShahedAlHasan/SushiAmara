package sushi.amara.com.customarapp.molie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("amount")
    @Expose
    private Amount amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("method")
    @Expose
    private Object method;
    @SerializedName("metadata")
    @Expose
    private Object metadata;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("isCancelable")
    @Expose
    private Boolean isCancelable;
    @SerializedName("expiresAt")
    @Expose
    private String expiresAt;
    @SerializedName("profileId")
    @Expose
    private String profileId;
    @SerializedName("sequenceType")
    @Expose
    private String sequenceType;
    @SerializedName("redirectUrl")
    @Expose
    private String redirectUrl;
    @SerializedName("webhookUrl")
    @Expose
    private String webhookUrl;
    @SerializedName("_links")
    @Expose
    private Links links;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getMethod() {
        return method;
    }

    public void setMethod(Object method) {
        this.method = method;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsCancelable() {
        return isCancelable;
    }

    public void setIsCancelable(Boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
