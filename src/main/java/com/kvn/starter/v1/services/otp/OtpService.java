package com.kvn.starter.v1.services.otp;

import com.kvn.starter.v1.entities.user.Otp;
import com.kvn.starter.v1.enums.otp.OtpPurpose;

public interface OtpService {
  Otp createOtp(String email, OtpPurpose otpPurpose);

  void validateOtp(String email, String code, OtpPurpose purpose);
}
