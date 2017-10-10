package srt.inz.connnectors;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";
    String REGISTER_MESSAGE="Register in...";

    //Urls
    String BASE_URL="https://mytripuser.000webhostapp.com/?dir=studentsport/stport/";
    
    String LOGIN_URL=BASE_URL+"login.php?";
    String STUDENTREGISTER_URL=BASE_URL+"studreg.php?";
    String FILESAVE_URL=BASE_URL+"filesave.php?";    
    String PROFUP_URL=BASE_URL+"studproup.php?";
    String STUDENTDETAILS_URL=BASE_URL+"stddet.php?";    
    String POSTTOPIC_URL=BASE_URL+"post_topic.php?";
    String POSTRETTOPIC_URL=BASE_URL+"ret_post.php?";        
    String RETCOMMENTS_URL=BASE_URL+"ret_cmnts.php?";    
    String COMMENTSUPDATE_URL=BASE_URL+"comment_up.php?";
    String EVENTSRET_URL=BASE_URL+"ret_events.php?";
   
    String DOWNLOADITEM_URL =BASE_URL+"ret_downloaditem.php?";    
    String COURSERET_URL =BASE_URL+"course_ret.php?";    
    String COURSEDET_URL=BASE_URL+"crsdet.php?";
    String REGISTERLIST_URL=BASE_URL+"ret_reglist.php?";
    String USERSTATUP_URL=BASE_URL+"up_userstat.php?";
    String EVENTUPDATE_URL=BASE_URL+"event_up.php?";
    
    String EVENTDROP_URL=BASE_URL+"dropevent.php?";  
    String COURSEUPDATE_URL=BASE_URL+"crs_up.php?";
    
    
    //Details
    String PASSWORD="Password";
    String USERNAME="Username";
    String LOGINSTATUS="LoginStatus";
    String SERVICESTATUS="ServiceStatus";
    String MYLOCATIONLAT="MyLocationLat";
    String MYLOCATIONLON="MyLocationLon";
    String USERTYPE="UserType";
    
    //SharedPreference
    String PREFERENCE_PARENT="Parent_Pref";
	
   
}
