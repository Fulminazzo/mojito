package it.fulminazzo.javaparser.environment;

import it.fulminazzo.javaparser.wrappers.BiObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A basic scope containing name value pairs of variables.
 *
 * @param <T> the type of the value
 */
class Scope<T> implements Scoped<T> {
    private final Map<ObjectData, T> internalMap;
    private final ScopeType scopeType;

    /**
     * Instantiates a new Scope.
     *
     * @param scopeType the scope type
     */
    public Scope(final @NotNull ScopeType scopeType) {
        this.internalMap = new LinkedHashMap<>();
        this.scopeType = scopeType;
    }

    @Override
    public @NotNull Optional<T> search(@NotNull final String name) {
        return getKey(name).map(this.internalMap::get);
    }

    @Override
    public void declare(@NotNull Info info, @NotNull String name, @NotNull T value) throws ScopeException {
        if (isDeclared(name)) throw alreadyDeclaredVariable(name);
        else this.internalMap.put(new ObjectData(info, name), value);
    }

    @Override
    public void update(@NotNull String name, @NotNull T value) throws ScopeException {
        ObjectData key = getKey(name).orElseThrow(() -> noSuchVariable(name));
        if (key.getInfo().compatibleWith(value)) this.internalMap.put(key, value);
        else throw new ScopeException(String.format("Cannot assign %s to %s", value, key.getInfo()));
    }

    @Override
    public @NotNull ScopeType scopeType() {
        return this.scopeType;
    }

    /**
     * Searches the internal map for the associated {@link ObjectData} with the given name.
     *
     * @param name the name
     * @return an optional containing the data (if found)
     */
    public @NotNull Optional<ObjectData> getKey(@NotNull String name) {
        return this.internalMap.keySet().stream().filter(d -> d.getName().equals(name)).findFirst();
    }

    /**
     * Represents the information of an object.
     *
     */
    static class ObjectData extends BiObjectWrapper<Info, String> {

        /**
         * Instantiates a new Object data.
         *
         * @param info the info
         * @param name the name
         */
        public ObjectData(final @NotNull Info info, final @NotNull String name) {
            super(info, name);
        }

        public @NotNull Info getInfo() {
            return this.first;
        }

        public @NotNull String getName() {
            return this.second;
        }

    }

}
