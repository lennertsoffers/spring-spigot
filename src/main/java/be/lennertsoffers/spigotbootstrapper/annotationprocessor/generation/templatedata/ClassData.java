package be.lennertsoffers.spigotbootstrapper.annotationprocessor.generation.templatedata;

public record ClassData(
        String simpleName,
        String packageName
) {

    public String fullyQualifiedName() {
        return this.packageName() + "." + this.simpleName();
    }

}
