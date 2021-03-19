package vn.isofh.medical.education.service;

import vn.isofh.common.service.BaseService;
import vn.isofh.medical.education.dao.model.SubjectEntity;
import vn.isofh.medical.education.dto.SubjectDTO;

public interface SubjectService extends BaseService<SubjectDTO> {
    SubjectEntity findEntityByid(Long id);
}
