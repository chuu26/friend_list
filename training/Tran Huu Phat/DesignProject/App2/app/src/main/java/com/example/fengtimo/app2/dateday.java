package com.example.fengtimo.app2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;




public class dateday {
    private String content;
    private String con;
    private Date dateFinish;
    private Date hourFinish;



    public String getcontent() {
        return content;
    }
    public String getcon() {
        return con;
    }
    public void setcontent(String content) {
        this.content = content;
    }
    public void setcon(String con) {
        this.con = con;
    }
    public Date getDateFinish() {
        return dateFinish;
    }
    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }
    public Date getHourFinish() {
        return hourFinish;
    }
    public void setHourFinish(Date hourFinish) {
        this.hourFinish = hourFinish;
    }



    public dateday(String con ,String content, Date dateFinish, Date hourFinish) {
        super();
        this.con = con;
        this.content = content;
        this.dateFinish = dateFinish;
        this.hourFinish = hourFinish;
    }
    public dateday() {
        super();
    }



    ///lấy định dạng ngày
    public String getDateFormat(Date d)
    {
        SimpleDateFormat dft=new
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }



    ///lấy định dạng giờ
    public String getHourFormat(Date d)
    {
        SimpleDateFormat dft=new
                SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dft.format(d);
    }




    @Override
    public String toString() {
        return getDateFormat(this.dateFinish)+"---"+ getHourFormat(this.hourFinish);
    }
}
