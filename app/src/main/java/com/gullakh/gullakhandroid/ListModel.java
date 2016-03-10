package com.gullakh.gullakhandroid;

import java.io.Serializable;

/**
 * Created by riverdale on 24/5/14.
 */
public class ListModel implements Serializable{

    private  String agenda_date="";
    private String id="";
    private  String time="";
    private  String title="";
    private  String speaker="";
    private  String desc="";
    private  String loctn="";
    private  String datetime="";
    private  String ag_sid="";

    private  String hotnam="";
    private  String hotno="";
    private  String hotdes="";
    private  String hotaddr="";
    private  String hoteml="";

    private  String other_nam="";
    private  String other_des="";

    private  String newsnam="";
    private  String newsdt="";
    private  String newsdes="";
    private  String newslink="";

    private  String speaknam="";
    private  String speakdes="";
    private  String speakdt="";

    private  String sponnam="";
    private  String spondes="";
    private  String sponpart="";
    private  String sponpath="";


    private  String squtn="";
    private  String sdate="";



    /*********** Set Methods ******************/

   public void setAgenda_Date(String agenda_date)
    {
        this.agenda_date= agenda_date;
    }
    public void setId(String id)
    {
        this.id= id;
    }
    public void setTime(String time)
    {
        this.time = time;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setprocessing_fee(String speaker)
    {
        this.speaker = speaker;
    }
    public void setemi_value(String desc)
    {
        this.desc= desc;
    }

    public void setbp(String loctn)
    {
        this.loctn = loctn;
    }
    public void setA_datetime(String datetime)
    {
        this.datetime= datetime;
    }
    public void setA_serverid(String ag_sid)
    {
        this.ag_sid= ag_sid;
    }

    public void setHotelnam(String hotnam)
    {
        this.hotnam= hotnam;
    }
    public void sethotno(String hotno)
    {
        this.hotno = hotno;
    }
    public void setHoteldes(String  hotdes)
    {
        this. hotdes= hotdes;
    }
    public void setHoteladdr(String hotaddr)
    {
        this.hotaddr = hotaddr;
    }
    public void sethoteml(String hoteml)
    {
        this.hoteml = hoteml;
    }

    public void setOthernam(String other_nam)
    {
        this.other_nam= other_nam;
    }
    public void setOtherdes(String other_des)
    {
        this.other_des = other_des;
    }


    public void setnewsnam(String newsnam)
    {
        this.newsnam = newsnam;
    }
    public void setnewsdt(String  newsdt)
    {
        this. newsdt=newsdt;
    }
    public void setnewsdes(String newsdes)
    {
        this.newsdes= newsdes;
    }
    public void setnewslink(String newslink)
    {
        this.newslink= newslink;
    }

    public void setaccount_lender(String speaknam)
    {
        this.speaknam = speaknam;
    }
    public void setbanknam(String  speakdes)
    {
        this. speakdes=speakdes;
    }
    public void setfloating_interest_rate(String speakdt)
    {
        this.speakdt= speakdt;
    }

    public void setsponnam(String sponnam)
    {
        this.sponnam = sponnam;
    }
    public void setspondes(String  spondes)
    {
        this. spondes=spondes;
    }
    public void setsponpart(String sponpart)
    {
        this.sponpart= sponpart;
    }
    public void setsponpath(String sponpath)
    {
        this.sponpath= sponpath;
    }



    public void setsqutn(String spsqutn)
    {
        this.squtn= spsqutn;
    }
    public void setsdate(String sdate)
    {
        this.sdate= sdate;
    }

    /*********** Get Methods ****************/

    public String getAgenda_date()
    {
        return this.agenda_date;
    }
    public String getId()
    {
        return this.id;
    }

    public String getTime()
    {
        return this.time;
    }

    public String getTitle()
    {
        return this.title;
    }
    public String getprocessing_fee()
    {
        return this.speaker;
    }
    public String getemi_value()
    {
        return this.desc;
    }

    public String getbp(){return this.loctn;
    }
    public String getA_datetime()
    {
        return this.datetime;
    }
    public String getA_serverid()
    {
        return this.ag_sid;
    }

    public String gethotnam()
    {
        return this.hotnam;
    }
    public String gethotno()
    {
        return this.hotno;
    }
    public String gethotdes()
    {
        return this.hotdes;
    }
    public String gethotaddr()
    {
        return this.hotaddr;
    }
    public String gethotemal()
    {
        return this.hoteml;
    }

    public String getOthernam()
    {
        return this.other_nam;
    }
    public String getOtherdes()
    {
        return this.other_des;
    }

    public String getnewsnam()
    {
        return this.newsnam;
    }
    public String getnewsdt()
    {
        return this.newsdt;
    }
    public String getnewsdes()
    {
        return this.newsdes;
    }
    public String getnewslink()
    {
        return this.newslink;
    }

    public String getaccount_lender()
    {
        return this.speaknam;
    }
    public String getbanknam()
    {
        return this.speakdes;
    }
    public String getfloating_interest_rate()
    {
        return this.speakdt;
    }


    public String getsponnam()
    {
        return this.sponnam;
    }
    public String getspondes()
    {
        return this.spondes;
    }
    public String getsponpart()
    {
        return this.sponpart;
    }
    public String getsponpath()
    {
        return this.sponpath;
    }


    public String getsqutn()
    {
        return this.squtn;
    }
    public String getsdate()
    {
        return this.sdate;
    }



}


