import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator

abstract org.fulib.scenarios.ast.Node {
	ScenarioGroup(name: String, scenarios: [Scenario], /* register: Register */)
	Scenario(name: String, sentences: [Sentence])

	abstract decl.Decl(name: String, type: String) {
		VarDecl(name: String, type: String, expr: Expr)
	}

	abstract decl.Name {
		UnresolvedName(value: String, text: String)
		ResolvedName(decl: Decl)
	}

	MultiDescriptor(type: Name, names: [String], attributes: [NamedExpr])

	abstract sentence.Sentence {
		ThereSentence(descriptors: [MultiDescriptor]) // like CreateSentence, but without an actor
		ExpectSentence(predicates: [ConditionalExpr]) // i.e. an assertion
		DiagramSentence(object: Expr, fileName: String) // i.e. an object diagram dump
		HasSentence(object: Expr, clauses:[NamedExpr]) // e.g. Albert has mood happy
		IsSentence(descriptor: VarDecl) // like ThereSentence, but only one declaration.

		/*
		PhraseSentence(phrase: Phrase)
		*/
	}

	abstract phrase.Phrase {
		/*
		CreatePhrase(actor: String, vars: [VarDecl]) // i.e. declarations with constructor calls
		WritePhrase(actor: String, lhs: Expr, rhs: Expr) // i.e. an assignment

		CallPhrase(actor: String, receiver: Expr, name: Name, parameters: [NamedExpr]) // i.e. a method call
		*/
	}

	NamedExpr(name: Name, expr: Expr)

	abstract expr.Expr {
		abstract primary.PrimaryExpr {
			NumberLiteral(value: double)
			StringLiteral(value: String)
			NameAccess(name: Name)
		}

		access.AttributeAccess(name: Name, receiver: Expr)
		access.ExampleAccess(value: Expr, expr: Expr)

		call.CreationExpr(className: Name, attributes: [NamedExpr])

		abstract conditional.ConditionalExpr {
			AttributeCheckExpr(receiver: Expr, attribute: Name, value: Expr)
			// ConditionalOperatorExpr(lhs: Expr, operator: ConditionalOperator, rhs: Expr)
		}

		abstract collection.CollectionExpr {
			ListExpr(elements: [Expr])
			// RangeExpr(start: Expr, end: Expr)
		}

		/*
		abstract collection.CollectionExpr {
			RangeExpr(from: Expr, through: Expr)
			ClosedRangeExpr(from: Expr, to: Expr)
			ListExpr(elements: [Expr])
		}
		*/
	}
}
