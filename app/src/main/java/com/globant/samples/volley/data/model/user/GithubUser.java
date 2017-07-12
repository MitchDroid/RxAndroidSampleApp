package com.globant.samples.volley.data.model.user;

/**
 * Created by miller.barrera on 5/06/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.globant.samples.volley.data.model.item.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GithubUser implements Parcelable {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public GithubUser(Integer totalCount, Boolean incompleteResults, List<Item> items) {
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    protected GithubUser(Parcel in) {
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<GithubUser> CREATOR = new Creator<GithubUser>() {
        @Override
        public GithubUser createFromParcel(Parcel in) {
            return new GithubUser(in);
        }

        @Override
        public GithubUser[] newArray(int size) {
            return new GithubUser[size];
        }
    };

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GithubUser that = (GithubUser) o;

        if (totalCount != null ? !totalCount.equals(that.totalCount) : that.totalCount != null)
            return false;
        if (incompleteResults != null ? !incompleteResults.equals(that.incompleteResults) : that.incompleteResults != null)
            return false;
        return items != null ? items.equals(that.items) : that.items == null;

    }

    @Override
    public int hashCode() {
        int result = totalCount != null ? totalCount.hashCode() : 0;
        result = 31 * result + (incompleteResults != null ? incompleteResults.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(items);
    }
}