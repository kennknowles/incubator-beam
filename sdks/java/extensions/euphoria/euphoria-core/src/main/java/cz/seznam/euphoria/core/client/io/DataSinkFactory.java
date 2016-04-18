package cz.seznam.euphoria.core.client.io;

import cz.seznam.euphoria.core.util.Settings;

import java.io.Serializable;
import java.net.URI;

/**
 * A factory for {@link DataSink} instances.
 */
public interface DataSinkFactory extends Serializable {

  <T> DataSink<T> get(URI uri, Settings settings);

}
