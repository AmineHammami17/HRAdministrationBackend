package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}
