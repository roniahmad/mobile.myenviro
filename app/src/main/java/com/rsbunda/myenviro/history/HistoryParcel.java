package com.rsbunda.myenviro.history;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class HistoryParcel implements Parcelable {

    private Integer id;
    private String ouFk;
    private Date dateVisit;
    private Integer isCancelled;
    private Integer isNext;
    private Integer isSkip;
    private Integer hasGivenRating;
    private String codeAlpha;
    private String ticketNo;
    private String poliName;
    private Integer doctorFk;
    private String doctorName;

    private Context mContext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOuFk() {
        return ouFk;
    }

    public void setOuFk(String ouFk) {
        this.ouFk = ouFk;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public Integer getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Integer getIsNext() {
        return isNext;
    }

    public void setIsNext(Integer isNext) {
        this.isNext = isNext;
    }

    public Integer getIsSkip() {
        return isSkip;
    }

    public void setIsSkip(Integer isSkip) {
        this.isSkip = isSkip;
    }

    public Integer getHasGivenRating() {
        return hasGivenRating;
    }

    public void setHasGivenRating(Integer hasGivenRating) {
        this.hasGivenRating = hasGivenRating;
    }

    public String getCodeAlpha() {
        return codeAlpha;
    }

    public void setCodeAlpha(String codeAlpha) {
        this.codeAlpha = codeAlpha;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getPoliName() {
        return poliName;
    }

    public void setPoliName(String poliName) {
        this.poliName = poliName;
    }

    public Integer getDoctorFk() {
        return doctorFk;
    }

    public void setDoctorFk(Integer doctorFk) {
        this.doctorFk = doctorFk;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public HistoryParcel(){}



    public HistoryParcel(Context context, Integer id, String ouFk,
                         Date dateVisit, Integer isCancelled,  Integer isNext, Integer isSkip, Integer hasGivenRating,
                         String codeAlpha, String ticketNo, String poliName,
                         Integer doctorFk, String doctorName){

        this.mContext = context;
        this.id = id;
        this.ouFk = ouFk;
        this.dateVisit = dateVisit;
        this.isCancelled = isCancelled;
        this.isNext = isNext;
        this.isSkip = isSkip;
        this.hasGivenRating = hasGivenRating;
        this.codeAlpha = codeAlpha;
        this.ticketNo = ticketNo;
        this.poliName = poliName;
        this.doctorFk = doctorFk;
        this.doctorName = doctorName;

    }



    public HistoryParcel(Parcel source){
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.ouFk = source.readString();
        this.dateVisit = new Date(source.readLong());
        this.isCancelled = (Integer) source.readValue(Integer.class.getClassLoader());
        this.isNext = (Integer) source.readValue(Integer.class.getClassLoader());
        this.isSkip = (Integer) source.readValue(Integer.class.getClassLoader());
        this.hasGivenRating = (Integer) source.readValue(Integer.class.getClassLoader());
        this.codeAlpha = source.readString();
        this.ticketNo = source.readString();
        this.poliName = source.readString();
        this.doctorFk = (Integer) source.readValue(Integer.class.getClassLoader());
        this.doctorName = source.readString();
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final HistoryParcel that = (HistoryParcel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (ouFk != null ? !ouFk.equals(that.ouFk) : that.ouFk != null) {
            return false;
        }
        if (dateVisit != null ? !dateVisit.equals(that.dateVisit) : that.dateVisit != null) {
            return false;
        }
        if (isCancelled != null ? !isCancelled.equals(that.isCancelled) :
                that.isCancelled != null) {
            return false;
        }
        if (isNext!= null ? !isNext.equals(that.isNext) :
                that.isNext != null) {
            return false;
        }
        if (isSkip!= null ? !isSkip.equals(that.isSkip) :
                that.isSkip!= null) {
            return false;
        }
        if (hasGivenRating!= null ? !hasGivenRating.equals(that.hasGivenRating) :
                that.hasGivenRating != null) {
            return false;
        }
        if (codeAlpha!= null ? !codeAlpha.equals(that.codeAlpha) :
                that.codeAlpha != null) {
            return false;
        }
        if (ticketNo != null ? !ticketNo.equals(that.ticketNo) :
                that.ticketNo != null) {
            return false;
        }
        if (poliName != null ? !poliName.equals(that.poliName) : that.poliName != null) {
            return false;
        }
        if (doctorFk!= null ? !doctorFk.equals(that.doctorFk) :
                that.doctorFk != null) {
            return false;
        }

        return doctorName != null ? doctorName.equals(that.doctorName) : that.doctorName == null;

    }



    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ouFk != null ? ouFk.hashCode() : 0);
        result = 31 * result + (dateVisit != null ? dateVisit.hashCode() : 0);
        result = 31 * result + (isCancelled != null ? isCancelled.hashCode() : 0);
        result = 31 * result + (isNext != null ? isNext.hashCode() : 0);
        result = 31 * result + (isSkip != null ? isSkip.hashCode() : 0);
        result = 31 * result + (hasGivenRating != null ? hasGivenRating.hashCode() : 0);
        result = 31 * result + (codeAlpha != null ? codeAlpha.hashCode() : 0);
        result = 31 * result + (ticketNo != null ? ticketNo.hashCode() : 0);
        result = 31 * result + (poliName != null ? poliName.hashCode() : 0);
        result = 31 * result + (doctorFk != null ? doctorFk.hashCode() : 0);
        result = 31 * result + (doctorName != null ? doctorName.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(id);
        parcel.writeString(ouFk);
        parcel.writeLong(dateVisit.getTime());
        parcel.writeValue(isCancelled);
        parcel.writeValue(isNext);
        parcel.writeValue(isSkip);
        parcel.writeValue(hasGivenRating);
        parcel.writeString(codeAlpha);
        parcel.writeString(ticketNo);
        parcel.writeString(poliName);
        parcel.writeValue(doctorFk);
        parcel.writeString(doctorName);
    }

    public static final Parcelable.Creator<HistoryParcel> CREATOR = new Parcelable.Creator<HistoryParcel>(){

        @Override
        public HistoryParcel createFromParcel(Parcel source) {
            return new HistoryParcel(source);
        }

        @Override
        public HistoryParcel[] newArray(int size) {
            return new HistoryParcel[size];
        }
    };
}
