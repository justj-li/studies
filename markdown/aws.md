# AWS CLI (Command Line Interface)
([more here](https://docs.aws.amazon.com/cli/latest/userguide/awscli-install-linux.html))

1. install python3+
2. install pip
3. install AWS CLI with pip
```shell
$ pip install awscli --upgrade --user
$ aws --version
```
4. add the _AWS CLI_ executable to the command line path

5. Configure aws providing the required information
   ([more here](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html))

```shell
$ aws configure
AWS Access Key ID [None]: AKIAIOSFODNN7EXAMPLE
AWS Secret Access Key [None]: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
Default region name [None]: us-east-1
Default output format [None]: <Enter>
```

## Example
To upload a file to AWS S3:

```shell
$ aws s3 cp myvideo.mp4 s3://mybucket/
```
Finally, let's see if this renders html too:

<html>
<dl>
  <dt>Definition list</dt>
  <dd>Is something people use sometimes.</dd>

  <dt>Markdown in HTML</dt>
  <dd>Does *not* work **very** well. Use HTML <em>tags</em>.</dd>
</dl>
<img src="http://yuml.me/diagram/scruffy/usecase/(Register)>(confirm event)" >
<img src="http://yuml.me/diagram/scruffy/usecase/[Source](message>[Dest]" >
</html>