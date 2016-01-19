package uk.co.jezuk.mango.algorithms;

import java.util.Iterator;
import uk.co.jezuk.mango.Predicate;

public abstract class RemoveIf { public abstract <T> void execute(Iterator<T> iterator,
                                 Predicate<? super  T> test)
     {
        if ((iterator == null) || (test == null))
         return;

        while (iterator.hasNext())
         {
            T obj = iterator.next();
            if (test.test(obj))
                iterator.remove(); // while ... // execute
        }
    }

    private RemoveIf() {}
} // RemoveIf
