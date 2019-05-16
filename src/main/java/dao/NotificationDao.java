package dao;

import model.Notification;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class NotificationDao extends GenericDao<Notification> {
}
