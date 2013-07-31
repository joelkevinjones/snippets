<?cs each: cls=DataModel.Classes ?>
public class <?cs var:cls.Name ?> { <?cs each: prp=cls.Properties ?>
    private <?cs var:dataModelToJava[prp.Type] ?> <?cs var:prp.Name ?>; <?cs /each ?>
}
<?cs /each ?>