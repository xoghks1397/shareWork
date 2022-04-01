package com.sharework.service;

import java.util.ArrayList;
import java.util.List;

import com.sharework.response.model.tag.giveTag;
import com.sharework.response.model.meta.BasicMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sharework.base.model.BaseTag;
import com.sharework.base.model.BaseTagSub;
import com.sharework.dao.JobTagDao;
import com.sharework.dao.TagDao;
import com.sharework.dao.TagSubDao;
import com.sharework.response.model.ErrorResponse;
import com.sharework.response.model.giveTagListPayload;
import com.sharework.response.model.giveTagListResponse;

@Service
public class TagService {

	@Autowired
	TagDao tagDao;
	@Autowired
	TagSubDao tagSubDao;
	@Autowired
	JobTagDao jobTagDao;

	public ResponseEntity giveTagList() {
		ResponseEntity response = null;
		ErrorResponse error = null;
		List<BaseTag> baseTagList =  tagDao.findAll();
		List<giveTag> tagList = new ArrayList<giveTag>();

		for (BaseTag tag : baseTagList) {
			List<BaseTagSub> tagSub = tagSubDao.getByBaseTagId(tag.getId());
			giveTag giveTag = new giveTag(tag.getId(),tag.getCategory(),tagSub);
			tagList.add(giveTag);
		}

		BasicMeta meta = new BasicMeta(true,"태그리스트 제공이 완료되었습니다.");
		giveTagListPayload giveTagListPayload = new giveTagListPayload(tagList);
		giveTagListResponse giveTagListResponse = new giveTagListResponse( giveTagListPayload,meta);
		response = new ResponseEntity<>(giveTagListResponse, HttpStatus.OK);
		return response;
	}

	public Boolean insertJobTag(long[] tagList, long jobId) {
		for (long tagSubId : tagList) {
			// tagSubId가 없다면
			if (tagSubDao.findById(tagSubId).isEmpty())
				return false;
//			jobTagDao.save(JobTag.bㄴuilder().jobId(jobId).tagSubId(tagSubId).build());
		}
		return true;
	}

}
