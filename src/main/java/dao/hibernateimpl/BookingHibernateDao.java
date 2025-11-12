package dao.hibernateimpl;

import dao.BookingDao;
import domain.Booking;

public class BookingHibernateDao extends GenericHibernateDao<Booking, Long> implements BookingDao {

    public BookingHibernateDao(){
        super(Booking.class);
    }

}
