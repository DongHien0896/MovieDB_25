package com.framgia.hien.moviedb.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cast implements Serializable {
    @SerializedName("cast_id")
    @Expose
    private Integer mCastId;
    @SerializedName("character")
    @Expose
    private String mCharacter;
    @SerializedName("credit_id")
    @Expose
    private String mCreditId;
    @SerializedName("gender")
    @Expose
    private Integer mGender;
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("order")
    @Expose
    private Integer mOrder;
    @SerializedName("profile_path")
    @Expose
    private String mProfilePath;

    public Integer getCastId() {
        return mCastId;
    }

    public void setCastId(Integer castId) {
        this.mCastId = castId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        this.mCharacter = character;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        this.mCreditId = creditId;
    }

    public Integer getGender() {
        return mGender;
    }

    public void setGender(Integer gender) {
        this.mGender = gender;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Integer getOrder() {
        return mOrder;
    }

    public void setOrder(Integer order) {
        this.mOrder = order;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        this.mProfilePath = profilePath;
    }
}
