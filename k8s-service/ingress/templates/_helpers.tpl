{{/*
Expand the name of the chart
*/}}
{{- define "ingress-controller.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end -}}

{{/*
Create chart name and version as used by the chart label
*/}}
{{- define "ingress-controller.chart" }}
{{- printf "%s-%s" .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end -}}

{{/*
Selector labels
*/}}
{{- define "ingress-controller.selector-labels" -}}
app.kubernetes.io/name: {{ include "ingress-controller.name" . }}
app.kubernetes.io/instance: {{ .Chart.Name | quote}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "ingress-controller.labels" -}}
helm.sh/chart: {{ include "ingress-controller.chart" }}
{{ include "ingress-controller.selector-labels" . }}
{{- if .Chart.AppVersion}}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote}}
{{- end }}
app.kubernetes.io/manged-by: {{ .Release.Service | quote}}
{{- end }}



{{/*
A default message string to be used when cheking for required value
*/}}
{{- define "helm-library.default-check-required-msg" -}}
{{- "No value found for '%s' in helm-library template" -}}
{{- end -}}





{{/*
will merge two YAML templates and out the result
- the top context
- the template name of the overrides(destination)
- the template name of the base(source)
*/}}
{{- define "helm-library.util.merge" -}}
{{- $top := first . -}}
{{- $overrides := fromYaml (include (index . 1) $top) | default (dict ) -}}
{{- $tpl := fromYaml (include (index . 2) $top) | default (dict ) -}}
{{- toYaml (merge $overrides $tpl) -}}
{{- end -}}






