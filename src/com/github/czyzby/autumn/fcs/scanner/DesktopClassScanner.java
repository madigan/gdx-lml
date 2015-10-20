package com.github.czyzby.autumn.fcs.scanner;

import java.lang.annotation.Annotation;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.autumn.scanner.ClassScanner;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;

/** Default, efficient class scanner for desktop. Does not rely on reflection and does not load scanned classes. Uses
 * {@code FastClasspathScanner}.
 *
 * @author MJ */
public class DesktopClassScanner implements ClassScanner {
    @Override
    public Array<Class<?>> findClassesAnnotatedWith(final Class<?> root,
            final Iterable<Class<? extends Annotation>> annotations) {
        final ObjectSet<Class<?>> result = GdxSets.newSet(); // Using set to remove duplicates.
        final FastClasspathScanner scanner = new FastClasspathScanner(root.getPackage().getName());
        for (final Class<? extends Annotation> annotation : annotations) {
            scanner.matchClassesWithAnnotation(annotation, new ClassAnnotationMatchProcessor() {
                @Override
                public void processMatch(final Class<?> matchingClass) {
                    result.add(matchingClass);
                }
            });
        }
        scanner.scan();
        return GdxArrays.newArray(result);
    }
}
