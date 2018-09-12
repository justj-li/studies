# DWH Tasks: Dataa
# (...in progress...)
Every problem in the [application domain of LiveIntent](scheme://CPHplaybook/terms/#programmatic-advertising) generates a coding task upon LiveIntent's data warehouse (DWH).

There are various kinds of problems according to which part of the application domain they involve: aggregation, configuration, GDPR, IDAAS, segments, etc. Thus a DWH coding task can be assigned both a name and a kind, the former descriptive of the problem to solve and the latter corresponding to the kind of the problem.

As an example, a problem such as _"user segments from providers need to be expanded with information from LiveIntent's ID graph"_ would generate a task that could be described as _"Segment Expansion"_ and would be a kind of problem involving the _"segment"_ part of the application domain.

The development of a coding task demands the creation and manipulation of data within DWH (either by ingesting new data into DWH or transforming existing data). With this purpose, it is necessary to write code to model the data, define its source and test its accessibility.

To complete the development of the task it is also necessary to write code for the job of handling the data to solve the problem along with an appropriate test suit. A discussion on [DWH jobs](https://github.com/mojn/cph-playbook) is out of the scope of this document and here the focus is on data.

## Names and Paths Conventions
As mentioned earlier, tasks are assigned names and kinds. Names are used to establish file names for sources, models, and tests, as well as classes and objects therein. Kinds are used to establish the path in the DWH code repository where scala code will be written and prefixes for the S3 bucket where data will be stored.

Next are the conventions used when handling DWH data:

- TASKNAME: `<NewIssue>`
- KIND: `aggregation | config | external | gdpr | idaas | segment | ... | <kind>`
- SOURCEPATH: `dwh/dwh-input/src/main/scala/com/liveintent/dwh/KIND/sources`
- MODELPATH: `dwh/dwh-input/src/main/scala/com/liveintent/dwh/KIND/models`
- SOURCETESTPATH: `dwh/dwh-input/src/test/scala/com/liveintent/dwh/KIND/sources`
- MODELTESTPATH: `dwh/dwh-input/src/test/scala/com/liveintent/dwh/KIND/models`
- S3PATH: `s3://KIND-dwh-liveintent-com/`

Observe that a task name is written in camel case (see [code styling](scheme://CPHplaybook/tasks/styling)). Additionally, it may be worth mentioning that S3 buckets are modeled as class `DwhKind` in  ` dwh-core/dwh-core/src/main/scala/com/liveintent/dwh/paths/Dwh.scala ` (within package `com.liveintent.dwh.paths`), and may have more prefixes according to specific design choices taken in the course of the development; this will be pointed out further on.





## Source
NB: To answer the questions that follow below regarding the design, it would be helpful to compare different types of sources/models existing in the repository. Compare for instance `HomeIPVisitors` and `SegmentConfigurationExpansion`

A source file is written in `SOURCEPATH/TASKNAMESource.scala` and it typically looks as shown in the code fragment that follows. Notice all occurrences of TASKNAME in the code; one occurrence^==1==^ is in lowercase and hyphened.

To create a class for a data source it should be established whether or not data should be only readable, how it should be encoded  and how it should be partitioned. The following are important design choices:

- Class extension^==2==^: `StandardDwhSource` | `ReadableLineBaseSource` | ...
- Mixins^==3==^: `DateBased` | `PreDatePartitioned` | ...
- Fields^==4==^: `dates`| `inFolder`| `outFolder`| `codec` | `preDatePartitioner`
- Codec type^==5==^: `TsvCodec` | `JsonCodec`
- Companion object extension^==6==^: `SourceCompanion` | `SourceCompanionBase`
- Target source^==7==^: `<TargetSource>`

<pre><code>
package com.liveintent.dwh.KIND.sources

import ...

case class TASKNAMESource private (override val <span style="background-color:#ffff00">dates</span><sup>4</sup>:DateRange, override val <span style="background-color:#ffff00">inFolder</span><sup>4</sup>:GlobFreePath, override val <span style="background-color:#ffff00">outFolder</span><sup>4</sup>:GlobFreePath,override val <span style="background-color:#ffff00">codec</span><sup>4</sup>:<span style="background-color:#ffff00">TsvCodec</span><sup>5</sup>[TASKNAME])
extends <span style="background-color:#ffff00">StandardDwhSource</span><sup>2</sup> with <span style="background-color:#ffff00">DateBased</span><sup>3</sup>
{
  type M=TASKNAME
}

object TASKNAMESource extends <span style="background-color:#ffff00">SourceCompanion</span><sup>6</sup>[TASKNAMESource,<span style="background-color:#ffff00">&lt;TargetSource&gt;</span><sup>7</sup>]{
  val name = <span style="background-color:#ffff00">"task-name"</span><sup>1</sup>
  def create(dates: DateRange, inFolder: Option[GlobFreePath], outFolder: Option[GlobFreePath]) = 
    TASKNAMESource(dates, inFolder.get, outFolder.get,TsvCodec())
}
</code></pre>

A more elaborated data source definition may accomodate different [aspects] of data (for instance...different ways of creation, fields with alternative representations,...). In such cases, the general data definition is described in a trait and particular [aspects] are extensions with particular structures.

```scala

```

.. data is defined with further partitions. Partitions determine prefixes in S3 buckets...

Remember:
- use autocompletion to ensure exhaustive imports





## Model
The model file is written in `MODELPATH/TASKNAME.scala`.

- Class extension: `Model`
- Companion object implicits: 


<pre><code>
package com.liveintent.dwh.KIND.models

import com.liveintent.dwh.models.Model

case class TASKNAME(
    field_1 : Type_1,
    ...
    field_n : Type_n
) extends Model

object TASKNAME {
  import ...
  
  implicit val jsonCodec: com.liveintent.util.JsonCodec[TASKNAME] = com.liveintent.util.JsonCodec.createCodec[TASKNAME]
  implicit val randomCreator: com.liveintent.util.RandomCreator[TASKNAME] = com.liveintent.util.RandomCreator.create[TASKNAME]
  implicit val instanceValidator: com.liveintent.util.InstanceValidator[TASKNAME] = com.liveintent.util.InstanceValidator.create[TASKNAME]
  implicit val modelValidator: com.liveintent.dwh.models.ModelValidator[TASKNAME] = com.liveintent.dwh.models.ModelValidator.create
  implicit val fieldExtractor: com.liveintent.util.FieldExtractor[TASKNAME] = com.liveintent.util.FieldExtractor.apply[TASKNAME]
}
</code></pre>

