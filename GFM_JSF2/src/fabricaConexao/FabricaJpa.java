package fabricaConexao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FabricaJpa {
	private static final String PERSISTENCE_UNIT_NAME = "GFM_JSF2";
	private static EntityManagerFactory factory;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return factory;
	}

	public static void shutdown() {
		if (factory != null) {
			factory.close();
		}
	}
}
