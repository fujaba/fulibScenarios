import org.fulib.scenarios.ast.expr.operator.BinaryOperator
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator
import org.fulib.scenarios.ast.expr.conditional.PredicateOperator
import org.fulib.scenarios.ast.sentence.CommentLevel
import org.fulib.scenarios.diagnostic.Position
import org.fulib.scenarios.diagnostic.Marker
import org.fulib.scenarios.library.Library

import org.fulib.scenarios.tool.Config

abstract org.fulib.scenarios.ast.Node {
	CompilationContext(config: Config, groups: [String:ScenarioGroup], libraries: [Library])
	ScenarioGroup(context: CompilationContext, sourceDir: String, packageDir: String,
	              files: [String:ScenarioFile], classes: [String:ClassDecl])
	ScenarioFile(group: ScenarioGroup, name: String, scenarios: [String:Scenario], classDecl: ClassDecl,
	             noconstruct external: boolean, noconstruct markers: [Marker])

	Positioned(noconstruct position: Position) {
		Scenario(file: ScenarioFile, name: String, body: SentenceList, methodDecl: MethodDecl)

		abstract decl.Decl(name: String, type: Type) {
			ClassDecl(group: ScenarioGroup, name: String, type: Type,
			          attributes: [String:AttributeDecl], associations: [String:AssociationDecl], methods: [MethodDecl],
			          noconstruct external: boolean, noconstruct frozen: boolean)

			AttributeDecl(owner: ClassDecl, name: String, type: Type)
			AssociationDecl(owner: ClassDecl, name: String, cardinality: int, target: ClassDecl, type: Type,
			                other: AssociationDecl?)
			MethodDecl(owner: ClassDecl, name: String, parameters: [ParameterDecl], type: Type, body: SentenceList)
			ParameterDecl(owner: MethodDecl, name: String, type: Type)

			VarDecl(name: String, type: Type, expr: Expr, noconstruct pattern: Pattern)
		}

		abstract decl.Name(readonly value: String, readonly decl: Decl) {
			UnresolvedName(value: String, text: String, readonly delegate decl: Decl)
			ResolvedName(readonly delegate value: String, decl: Decl)
			WildcardName(readonly delegate value: String, readonly delegate decl: Decl)
		}

		abstract type.Type(readonly delegate description: String) {
			UnresolvedType(name: String)
			ClassType(classDecl: ClassDecl)
			ListType(elementType: Type)
			import PrimitiveType
			DynamicType(bound: Type)
		}

		abstract sentence.Sentence {
			SentenceList(items: [Sentence])

			SectionSentence(text: String, level: CommentLevel)

			ThereSentence(descriptor: MultiDescriptor) // like CreateSentence, but without an actor
			ExpectSentence(predicates: [Expr]) // i.e. an assertion
			DiagramSentence(object: Expr, fileName: String) // i.e. an object diagram dump
			HasSentence(object: Expr, clauses:[NamedExpr]) // e.g. Albert has mood happy
			IsSentence(descriptor: VarDecl) // like ThereSentence, but only one declaration.
			AreSentence(descriptor: MultiDescriptor)

			PatternExpectSentence(patterns: [Pattern])

			abstract ActorSentence(actor: Name) {
				CreateSentence(actor: Name, descriptor: MultiDescriptor)

				CallSentence(actor: Name, call: CallExpr)
				AnswerSentence(actor: Name, result: Expr, varName: String)

				abstract MutatingSentence(actor: Name, source: Expr, target: Expr) {
					WriteSentence(actor: Name, source: Expr, target: Expr) // i.e. an assignment
					AddSentence(actor: Name, source: Expr, target: Expr) // i.e. adding elements to a list
					RemoveSentence(actor: Name, source: Expr, target: Expr) // i.e. removing elements from a list
				}

				TakeSentence(actor: Name, varName: Name?, example: Expr, collection: Expr, body: Sentence)
			}

			ConditionalSentence(condition: Expr, body: Sentence)

			AssignSentence(target: Decl, operator: BinaryOperator?, value: Expr) // i.e. a variable assignment
			ExprSentence(expr: Expr)
		}

		abstract expr.Expr(readonly delegate type: Type) {
			ErrorExpr(type: Type)

			abstract primary.PrimaryExpr {
				IntLiteral(value: int)
				DoubleLiteral(value: double)
				StringLiteral(value: String)
				NameAccess(name: Name)
				AnswerLiteral()
			}

			access.AttributeAccess(name: Name, receiver: Expr)
			access.ExampleAccess(value: Expr, expr: Expr)

			call.CreationExpr(type: Type, attributes: [NamedExpr])
			call.CallExpr(name: Name, receiver: Expr, arguments: [NamedExpr], body: SentenceList)

			operator.BinaryExpr(lhs: Expr, operator: BinaryOperator, rhs: Expr)

			abstract conditional.ConditionalExpr {
				AttributeCheckExpr(receiver: Expr, attribute: Name, value: Expr)
				ConditionalOperatorExpr(lhs: Expr, operator: ConditionalOperator, rhs: Expr)
				PredicateOperatorExpr(lhs: Expr, operator: PredicateOperator)
			}

			abstract collection.CollectionExpr {
				ListExpr(elements: [Expr])
				RangeExpr(start: Expr, end: Expr)

				MapAccessExpr(name: Name, receiver: Expr)
				FilterExpr(source: Expr, predicate: Expr)
			}
		}

		abstract pattern.Constraint {
			LinkConstraint(name: Name?, target: Name)
			AttributeEqualityConstraint(name: Name?, expr: Expr)
			AttributeConditionalConstraint(name: Name?, operator: ConditionalOperator, rhs: Expr)
			AttributePredicateConstraint(name: Name?, predicate: PredicateOperator)
		}
	}

	// Helpers

	MultiDescriptor(type: Type, names: [Name], attributes: [NamedExpr])
	NamedExpr(name: Name, expr: Expr, noconstruct otherName: Name?, noconstruct otherMany: boolean)
	pattern.Pattern(type: Type, name: Name, constraints: [Constraint])
}
