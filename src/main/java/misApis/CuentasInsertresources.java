package misApis;

import umg.edu.gt.BibliotecaDTO.CuentasDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Path("insertarCuentas")
public class CuentasInsertresources {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @POST
    @Path("insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarEstudiante(CuentasDTO cuenta) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // No es necesario establecer la fecha de registro ya que la BD la implementa

            session.save(cuenta);
            tx.commit();

            return Response.status(Response.Status.CREATED)
           .entity(cuenta).build();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("ERROR AL INSERTAR").build();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
