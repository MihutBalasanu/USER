package service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("notificationService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotificationService {
}
