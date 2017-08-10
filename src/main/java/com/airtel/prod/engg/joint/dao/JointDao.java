package com.airtel.prod.engg.joint.dao;

import com.airtel.prod.engg.joint.model.Manhole;

public interface JointDao {

	String saveInfo(Manhole manhole, String olmId) throws Exception;

}