package com.airtel.prod.engg.joint.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.airtel.prod.engg.joint.constant.JointConstants;
import com.airtel.prod.engg.joint.constant.Query;
import com.airtel.prod.engg.joint.dao.JointDao;
import com.airtel.prod.engg.joint.model.Cable;
import com.airtel.prod.engg.joint.model.Connection;
import com.airtel.prod.engg.joint.model.Duct;
import com.airtel.prod.engg.joint.model.Joint;
import com.airtel.prod.engg.joint.model.Manhole;
import com.airtel.prod.engg.joint.model.ManholeDuctInfo;

@Component
public class JointDaoImpl implements JointDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.airtel.prod.engg.dao.impl.JointDao#saveInfo(com.airtel.prod.engg.model.Manhole, java.lang.String)
	 */
	@Override
	public String saveInfo(Manhole manhole,String olmId) throws Exception{
		String manholeId = manhole.getManholeId();
		Map<String,Map<String,Integer>> ductCableMap = new HashMap<String, Map<String,Integer>>();;
		for(ManholeDuctInfo manholeDuctInfo : manhole.getManholeDuctInfo()){
			String direction = manholeDuctInfo.getDirection();
			String manHolePrimaryKey = manholeId + "_" + direction;
			jdbcTemplate.update(Query.MANHOLE_TABLE_INSERT_QUERY, new Object[]{manHolePrimaryKey,manholeId,olmId,manholeDuctInfo.getNoOfDucts(),direction});
			for(Duct duct : manholeDuctInfo.getDucts()){
				String ductId = duct.getDuctId();
				String ductPrimaryKey = manHolePrimaryKey + "_" + ductId;
				ductCableMap.put(ductPrimaryKey, duct.getCableLoopMap());
				jdbcTemplate.update(Query.DUCT_TABLE_INSERT_QUERY, new Object[]{ductPrimaryKey,manHolePrimaryKey,ductId,duct.getNoOfCables(),duct.getColor()});
			}
		}
		for(Joint joint : manhole.getJoints()){
			String jointId = manholeId + "_J" + joint.getJointOrder();
			jdbcTemplate.update(Query.JOINT_TABLE_INSERT_QUERY, new Object[]{jointId,joint.getNoOfCables()});
			for(Connection connection : joint.getConnections()){
				String cableId = connection.getEnd1().getCableId();
				String tubeId = connection.getEnd1().getTubeId();
				String color = connection.getEnd1().getColor();
				String direction = null;
				int type = 0;
				int cableOrder = 0;
				
				for(Cable cable : joint.getCableInfo()){
					if(cable.getCableId().equalsIgnoreCase(cableId)){
						direction = cable.getCableDirection();
						type = cable.getCableType();
						cableOrder = cable.getCableOrder();
						break;
					}
				}
				Integer loopDistance = null;
				String ductPrimaryKey = null;
				for(Map.Entry<String, Map<String,Integer>> entry : ductCableMap.entrySet()){
					loopDistance = entry.getValue().get(cableId);
					if(loopDistance != null){
						ductPrimaryKey = entry.getKey();
						break;
					}
				}
				String cablePrimaryKey1 = null;
				if(loopDistance != null){
					cablePrimaryKey1 = ductPrimaryKey + "_" + cableId + "_" + tubeId + "_" + color;
					jdbcTemplate.update(Query.CABLE_TABLE_INSERT_QUERY, new Object[]{cablePrimaryKey1,ductPrimaryKey,cableId,direction,type,tubeId,color,cableOrder});
				}
				
				

				String anotherEndcableId = connection.getEnd2().getCableId();
				String anotherEndtubeId = connection.getEnd2().getTubeId();
				String anotherEndcolor = connection.getEnd2().getColor();
				String anotherEnddirection = null;
				int anotherEndtype = 0;
				int anotherEndcableOrder = 0;
				
				for(Cable cable : joint.getCableInfo()){
					if(cable.getCableId().equalsIgnoreCase(anotherEndcableId)){
						anotherEnddirection = cable.getCableDirection();
						anotherEndtype = cable.getCableType();
						anotherEndcableOrder = cable.getCableOrder();
						break;
					}
				}
				Integer anotherEndloopDistance = null;
				String anotherEndductPrimaryKey = null;
				for(Map.Entry<String, Map<String,Integer>> entry : ductCableMap.entrySet()){
					anotherEndloopDistance = entry.getValue().get(anotherEndcableId);
					if(anotherEndloopDistance != null){
						anotherEndductPrimaryKey = entry.getKey();
						break;
					}
				}
				String cablePrimaryKey2 = null;
				if(anotherEndloopDistance != null){
					cablePrimaryKey2 = anotherEndductPrimaryKey + "_" + anotherEndcableId + "_" + anotherEndtubeId + "_" +anotherEndcolor;
					jdbcTemplate.update(Query.CABLE_TABLE_INSERT_QUERY, new Object[]{cablePrimaryKey2,anotherEndductPrimaryKey,anotherEndcableId,anotherEnddirection,anotherEndtype,anotherEndtubeId,anotherEndcolor,anotherEndcableOrder});
				}
				jdbcTemplate.update(Query.CONNECTION_TABLE_INSERT_QUERY, new Object[]{cablePrimaryKey1,cablePrimaryKey2,jointId});
			
			}
		}
		return JointConstants.SUCCESS;
	}
	
//	private String fetchCableInfo(Joint joint){
//		
//	}
}
