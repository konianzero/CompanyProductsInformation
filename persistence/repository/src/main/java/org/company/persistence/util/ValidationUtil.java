package org.company.persistence.util;

import lombok.experimental.UtilityClass;
import org.company.persistence.model.HasId;
import org.company.persistence.util.exception.IllegalRequestDataException;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkNotFoundWithId(int num, int id) {
        checkNotFound(num == 0, id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFound(Objects.isNull(object), id);
        return object;
    }

    public static <T> T checkNotFoundWithId(Optional<T> object, int id) {
        checkNotFound(object.isEmpty(), id);
        return object.get();
    }

    public static void checkNotFound(boolean isFound, int id) {
        if (isFound) {
            throw new EntityNotFoundException("Not found entity with id=" + id);
        }
    }
}
