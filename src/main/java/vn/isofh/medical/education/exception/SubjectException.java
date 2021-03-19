package vn.isofh.medical.education.exception;

import vn.isofh.common.exception.BaseException;
import vn.isofh.common.msg.Msg;

public class SubjectException {

  private final static int ERROR_CODE = 1000;

  public static class SubjectNotExist extends BaseException {

    public SubjectNotExist(Long id) {
      super(ERROR_CODE + 1, Msg.getMessage("subject.not.exists", new Object[]{id}));
    }

  }
}
