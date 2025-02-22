package caseapp
package demo

import caseapp.core.util.Formatter

final case class SharedOptions(
  other: String = ""
)

object SharedOptions {
  implicit val parser: Parser[SharedOptions] = Parser.derive
  implicit val help: Help[SharedOptions]     = Help.derive
}

@AppVersion("0.1.0")
@ArgsName("files")
final case class DemoOptions(
  first: Boolean,
  @ExtraName("V") @HelpMessage("Set a value") value: Option[String],
  @ExtraName("v") @HelpMessage("Be verbose") verbose: Int @@ Counter,
  @ExtraName("S") @ValueDescription("stages") stages: List[String],
  @Recurse shared: SharedOptions = SharedOptions()
)

object DemoOptions {
  implicit val parser: Parser[DemoOptions] = Parser.derive
  implicit val help: Help[DemoOptions]     = Help.derive
}

object Demo extends CaseApp[DemoOptions] {

  def run(options: DemoOptions, remainingArgs: RemainingArgs) =
    Console.err.println(this)
}

@AppName("Glorious App")
@AppVersion("0.1.0")
final case class MyAppOptions(
  @ValueDescription("a foo") @HelpMessage("Specify some foo") foo: Option[String],
  bar: Int
)

object MyApp extends CaseApp[MyAppOptions] {
  def run(options: MyAppOptions, remainingArgs: RemainingArgs) = {}
}

@AppName("Demo")
@AppVersion("1.0.0")
@ProgName("demo-cli")
sealed abstract class DemoCommand extends Product with Serializable

final case class First(
  @ExtraName("v") verbose: Int @@ Counter,
  @ValueDescription("a bar") @HelpMessage("Set bar") bar: String = "default-bar"
) extends DemoCommand

@CommandName("second")
@HelpMessage("second cmd help")
final case class Secondd(
  extra: List[Int],
  @ExtraName("S")
  @ValueDescription("stages")
  stages: List[String]
) extends DemoCommand

object ManualCommandStuff {

  case object Command1 extends CaseApp[Command1Opts] {
    def run(options: Command1Opts, args: RemainingArgs): Unit = {}
  }

  case object Command2 extends CaseApp[Command2Opts] {
    def run(options: Command2Opts, args: RemainingArgs): Unit = {}
  }
}

object ManualCommandNotAdtStuff {

  case object Command1 extends CaseApp[ManualCommandNotAdtOptions.Command1Opts] {
    def run(options: ManualCommandNotAdtOptions.Command1Opts, args: RemainingArgs): Unit = {}
  }

  case object Command2 extends CaseApp[ManualCommandNotAdtOptions.Command2Opts] {
    def run(options: ManualCommandNotAdtOptions.Command2Opts, args: RemainingArgs): Unit = {}
  }

  case object Command3StopAtUnreco extends CaseApp[ManualCommandNotAdtOptions.Command3Opts] {
    override def stopAtFirstUnrecognized                                                 = true
    def run(options: ManualCommandNotAdtOptions.Command3Opts, args: RemainingArgs): Unit = {}
  }

  case object Command4NameFormatter extends CaseApp[ManualCommandNotAdtOptions.Command4Opts] {
    override def nameFormatter: Formatter[Name] = (name: Name) => name.name
    def run(options: ManualCommandNotAdtOptions.Command4Opts, args: RemainingArgs): Unit = {}
  }

  case object Command5IgnoreUnrecognized extends CaseApp[ManualCommandNotAdtOptions.Command5Opts] {
    override def ignoreUnrecognized                                                      = true
    def run(options: ManualCommandNotAdtOptions.Command5Opts, args: RemainingArgs): Unit = {}
  }
}

object ManualSubCommandStuff {

  case object Command1 extends CaseApp[ManualSubCommandOptions.Command1Opts] {
    def run(options: ManualSubCommandOptions.Command1Opts, args: RemainingArgs): Unit = {}
  }

  case object Command2 extends CaseApp[ManualSubCommandOptions.Command2Opts] {
    def run(options: ManualSubCommandOptions.Command2Opts, args: RemainingArgs): Unit = {}
  }
}
