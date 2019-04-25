import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator

org.fulib.scenarios.ast.Node {
	Package(name: String, scenarios: [Scenario], /* register: Register */)
	Scenario(name: String, sentences: [Sentence])

	sentence.Sentence {
		ThereSentence(descriptors: [Descriptor]) // like CreatePhrase, but without an actor

		/*
		PhraseSentence(phrase: Phrase)
		IsSentence(descriptor: Descriptor) // like ThereSentence, but only one declaration.
		ExpectSentence(predicates: [ConditionalExpr]) // i.e. an assertion
		DiagramSentence(object: Expr, fileName: String) // i.e. an object diagram dump
		*/
	}

	phrase.Phrase {
		/*
		CreatePhrase(actor: String, descriptors: [Descriptor]) // i.e. declarations with constructor calls
		WritePhrase(actor: String, lhs: Expr, rhs: Expr) // i.e. an assignment

		CallPhrase(actor: String, receiver: Expr, name: Name, parameters: [NamedExpr]) // i.e. a method call
		*/
	}

	Name(value: String, text: String)
	Descriptor(name: Name, constructor: Constructor)
	Constructor(className: Name, parameters: [NamedExpr])
	NamedExpr(name: Name, expr: Expr)

	expr.Expr {
		primary.PrimaryExpr {
			NumberLiteral(value: double)
			StringLiteral(value: String)
			NameAccess(name: Name)
		}

		access.AttributeAccess(name: Name, receiver: Expr)
		access.ExampleAccess(value: Expr, expr: Expr)

		/*
		collection.CollectionExpr {
			RangeExpr(from: Expr, through: Expr)
			ClosedRangeExpr(from: Expr, to: Expr)
			ListExpr(elements: [Expr])
		}

		conditional.ConditionalExpr {
			AttributeCheckExpr(receiver: Expr, attribute: String, value: Expr)
			ConditionalOperatorExpr(lhs: Expr, operator: ConditionalOperator, rhs: Expr)
		}
		*/
	}
}
