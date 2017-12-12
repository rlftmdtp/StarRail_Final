package starrail.reservation.util;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import starrail.reservation.domain.TrainSCVO;
import starrail.sharetext.domain.StationPakageVO;

@Component
public class ReservationUtil {

	
	//역을 불러올 때 필요한 지역코드
	 public List<String> getCityCode() throws Exception{  
	      String xmlAddr ="http://openapi.tago.go.kr/openapi/service/TrainInfoService/getCtyCodeList?ServiceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D";
	      
	      URL url = new URL(xmlAddr);
	      
	      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	      factory.setNamespaceAware(true);
	      XmlPullParser xpp = factory.newPullParser();
	      BufferedInputStream bis = new BufferedInputStream(url.openStream());
	      xpp.setInput(bis, "utf-8");
	      
	      
	      int eventType = xpp.getEventType();
	      
	      ArrayList<String> list = new ArrayList<String>();
	      String tag = null;
	      String str = null;
	      
	      while(eventType != XmlPullParser.END_DOCUMENT){
	         if(eventType == XmlPullParser.START_TAG){
	            tag = xpp.getName();
	         } else if (eventType == XmlPullParser.TEXT){
	            if(tag.equals("citycode")){
	               str = xpp.getText();
	            }
	         } else if (eventType ==XmlPullParser.END_TAG){
	            tag = xpp.getName();
	            if(tag.equals("item")){
	               list.add(str);
	            }
	         }
	         
	         eventType=xpp.next();
	      }
	      
	      return list;
	   }
	
	
	
	 //지역코드를 이용해서 전체 역 뽑기
	   public List<StationPakageVO> stationList(List<String> citycode) throws Exception{   
		      String xmlAddr = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getCtyAcctoTrainSttnList?serviceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D&cityCode=";
		      List<StationPakageVO> list = new ArrayList<StationPakageVO>();
		      
		      
		      
		      for(int i=0; i<citycode.size(); i++){
		         
		         URL url = new URL(xmlAddr+citycode.get(i));
		         
		         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		         factory.setNamespaceAware(true);
		         XmlPullParser xpp = factory.newPullParser();
		         BufferedInputStream bis = new BufferedInputStream(url.openStream());
		         xpp.setInput(bis, "utf-8");
		         
		         String tag = null;
		         int eventType = xpp.getEventType();
		         String s_id=null;
		         String s_name=null;
		         
		         
		         while(eventType != XmlPullParser.END_DOCUMENT){
		            StationPakageVO station = new StationPakageVO();
		            if(eventType == XmlPullParser.START_TAG){
		               tag = xpp.getName();
		            } else if (eventType == XmlPullParser.TEXT){
		               if(tag.equals("nodeid")){
		                  s_id=xpp.getText();
		                  
		               } else if(tag.equals("nodename")){
		                  s_name=xpp.getText();
		               }
		            } else if (eventType ==XmlPullParser.END_TAG){
		               tag = xpp.getName();
		               if(tag.equals("item")){
		                  //station.setId(s_id);
		            	   station.setS_id(s_id);
		                  station.setS_name(s_name);
		                  list.add(station);
		                  
		               }
		            }
		            
		            eventType=xpp.next();
		         }
		      }
		   
		      Collections.sort(list,new Comparator<StationPakageVO>() {

				@Override
				public int compare(StationPakageVO o1, StationPakageVO o2) {
					
					return o1.getS_name().compareTo(o2.getS_name());
				}
			});
		      return list;
		      
		   }
	   
	   //기차정보 가져오기
	   public List<TrainSCVO> getTimeTable(String red_sstation, String red_sdate, String red_estation, String red_stime) throws Exception{
		      
		      String depPlaceId="&depPlaceId="+red_sstation;   //출발역ID
		      
		      String depPlandTime="&depPlandTime="+red_sdate.replaceAll("-", "");
		    		 
		      
		      String arrPlaceId="&arrPlaceId="+red_estation;   //도착역ID		      
		      
		      String xmlOrg = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo?serviceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D&numOfRows=200"
		                     + depPlaceId + depPlandTime + arrPlaceId;
		      
		      List<TrainSCVO> list = new ArrayList<TrainSCVO>();
		      
		      URL url = new URL(xmlOrg);
		      
		      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		      factory.setNamespaceAware(true);
		      XmlPullParser xpp = factory.newPullParser();
		      BufferedInputStream bis = new BufferedInputStream(url.openStream());
		      xpp.setInput(bis, "utf-8");
		      
		      String tag = null;
		      int eventType = xpp.getEventType();
		      String arrTime=null;
		      String depTime=null;
		      String trainType=null;
		      
		      while(eventType != XmlPullParser.END_DOCUMENT){
		    	  TrainSCVO vo = new TrainSCVO();
		         if(eventType == XmlPullParser.START_TAG){
		            tag = xpp.getName();
		         } else if (eventType == XmlPullParser.TEXT){
		            if(tag.equals("arrplandtime")){
		               arrTime=xpp.getText();
		            } else if(tag.equals("depplandtime")){
		               depTime=xpp.getText();
		            } else if(tag.equals("traingradename")){
		               trainType=xpp.getText();
		            }
		         } else if (eventType ==XmlPullParser.END_TAG){
		            tag = xpp.getName();
		            if(tag.equals("item")){
		               if(trainType.equals("무궁화호") || trainType.equals("새마을호") || trainType.equals("누리로")){
		                  if( Long.parseLong(depTime) >= Long.parseLong(red_sdate.replaceAll("-", "")+red_stime)){ //출발시간
		                     
		                     
		                     vo.setTrain_etime(arrTime);
		                     vo.setTrain_stime(depTime);
		                     vo.setTrainType(trainType);
		                     
		                     list.add(vo);   
		                  }
		               }
		            }
		         }
		         
		         eventType=xpp.next();
		      }
		      
		   return list;
		   }
	   
	   
	 /*public static void main(String[] args){
		   ReservationUtil util = new ReservationUtil();
		   try {
			List<String> list = util.getCityCode();
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i));
			}
			List<StationPakageVO> list2 = util.stationList(list);
			for(int i=0;i<list2.size();i++){
				System.out.println(list2.get(i).getS_name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	   };*/

}	  
	   
	   
	   
	   
