package service;

import config.HibernateUtil;
import dao.BookingDao;
import dao.SpaceDao;
import dao.UserDao;
import dao.hibernateimpl.BookingHibernateDao;
import dao.hibernateimpl.SpaceHibernateDao;
import dao.hibernateimpl.UserHibernateDao;
import domain.Booking;
import domain.BookingStatus;
import domain.Space;
import domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private final SessionFactory sessionFactory;
    private final UserDao userDao;
    private final SpaceDao spaceDao;
    private final BookingDao bookingDao;

    public BookingService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.userDao = new UserHibernateDao();
        this.spaceDao = new SpaceHibernateDao();
        this.bookingDao = new BookingHibernateDao();
    }

    public List<Booking> findConfirmedBookingByVenueAndRange(String venueName, LocalDateTime startTime, LocalDateTime endTime){

        Transaction tx = null;
        try{
            Session session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<Booking> bookings = this.bookingDao.findConfirmedBookingsByVenueAndRange(session, venueName, startTime, endTime);

            tx.commit();

            return bookings;

        } catch (RuntimeException e) {
            if(tx != null){
                tx.rollback();
            }
            throw e;
        }

    }

    public Long create(Long userId, Long spaceId, LocalDateTime start, LocalDateTime end, BigDecimal totalPrice, BookingStatus bookingStatus){

        Transaction tx = null;
        try{
            Session session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            User user = this.userDao.findById(session, userId);

            if(user == null){
                throw new EntityNotFoundException("No existe ningún User con el ID: " + userId);
            }

            Space space = this.spaceDao.findById(session, spaceId);

            if(space == null){
                throw new EntityNotFoundException("No existe ningún Space con el ID: " + spaceId);
            }

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setSpace(space);
            booking.setCreatedAt(LocalDateTime.now());
            booking.setStartTime(start);
            booking.setEndTime(end);
            booking.setTotalPrice(totalPrice);
            booking.setStatus(bookingStatus);

            Long id = this.bookingDao.create(session, booking);

            tx.commit();

            return id;

        } catch (RuntimeException e) {
            if(tx != null){
                tx.rollback();
            }
            throw e;
        }

    }

}
