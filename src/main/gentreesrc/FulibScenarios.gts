import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator

abstract org.fulib.scenarios.ast.Node {
	ScenarioGroup(sourceDir: String, packageDir: String, files: [ScenarioFile])
	ScenarioFile(name: String, scenarios: [Scenario])
	Scenario(name: String, body: SentenceList)

	abstract decl.Decl(name: String, type: String) {
		VarDecl(name: String, type: String, expr: Expr)
	}

	abstract decl.Name {
		UnresolvedName(value: String, text: String)
		ResolvedName(decl: Decl)
	}

	MultiDescriptor(type: Name, names: [String], attributes: [NamedExpr])

	abstract sentence.Sentence {
		SentenceList(items: [Sentence])

		ThereSentence(descriptors: [MultiDescriptor]) // like CreateSentence, but without an actor
		ExpectSentence(predicates: [ConditionalExpr]) // i.e. an assertion
		DiagramSentence(object: Expr, fileName: String) // i.e. an object diagram dump
		HasSentence(object: Expr, clauses:[NamedExpr]) // e.g. Albert has mood happy
		IsSentence(descriptor: VarDecl) // like ThereSentence, but only one declaration.

		CreateSentence(actor: Name, descriptor: MultiDescriptor)

		CallSentence(actor: Name, call: CallExpr)
		AnswerSentence(actor: Name, result: Expr)

		ExprSentence(expr: Expr)
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
		call.CallExpr(name: Name, receiver: Expr, body: SentenceList)

		abstract conditional.ConditionalExpr {
			AttributeCheckExpr(receiver: Expr, attribute: Name, value: Expr)
			// ConditionalOperatorExpr(lhs: Expr, operator: ConditionalOperator, rhs: Expr)
		}

		abstract collection.CollectionExpr {
			ListExpr(elements: [Expr])
			// RangeExpr(start: Expr, end: Expr)
		}
	}
}
