
package com.globant.samples.volley.data.model.repository;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "repository")
public class GithubUserRepo implements Parcelable{

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("owner")
    @Expose
    @Ignore
    private Owner owner;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    public GithubUserRepo() {
    }

    public GithubUserRepo(String userName, Integer id, String name, String fullName, Owner owner, String htmlUrl) {
        this.userName = userName;
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.owner = owner;
        this.htmlUrl = htmlUrl;
    }

    protected GithubUserRepo(Parcel in) {
        userName = in.readString();
        name = in.readString();
        fullName = in.readString();
        htmlUrl = in.readString();
    }

    public static final Creator<GithubUserRepo> CREATOR = new Creator<GithubUserRepo>() {
        @Override
        public GithubUserRepo createFromParcel(Parcel in) {
            return new GithubUserRepo(in);
        }

        @Override
        public GithubUserRepo[] newArray(int size) {
            return new GithubUserRepo[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(name);
        parcel.writeString(fullName);
        parcel.writeString(htmlUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GithubUserRepo that = (GithubUserRepo) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return htmlUrl != null ? htmlUrl.equals(that.htmlUrl) : that.htmlUrl == null;

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (htmlUrl != null ? htmlUrl.hashCode() : 0);
        return result;
    }
}
